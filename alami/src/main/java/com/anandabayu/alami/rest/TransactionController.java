package com.anandabayu.alami.rest;

import com.anandabayu.alami.entity.History;
import com.anandabayu.alami.entity.Transaction;
import com.anandabayu.alami.entity.User;
import com.anandabayu.alami.kafka.producer.MessageConsumer;
import com.anandabayu.alami.kafka.producer.MessageProducer;
import com.anandabayu.alami.service.TransactionService;
import com.anandabayu.alami.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TransactionController {

    private UserService userService;
    private TransactionService transactionService;

    private MongoTemplate mongoTemplate;

    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    public TransactionController(UserService userService, TransactionService transactionService, MongoTemplate mongoTemplate) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/transactions")
    public Map<String, Object> getTransactions(
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(required = false, defaultValue = "0") Integer user_id
    ) throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        LocalDate today = LocalDate.now();

        String start = start_date, end = end_date;

        if (start == null || start.isEmpty() || end == null || end.isEmpty()) {
            start = today.minusMonths(36).toString();
            end = today.toString();
        }

        List<Transaction> transactions = transactionService.findAll(start, end, user_id);
        Integer balance = transactionService.getCurrentBalance();

        map.put("success", true);
        map.put("message", "Transactions Data");
        map.put("data", transactions);
        map.put("balance", balance);

        return map;
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody Map<String, Object> payload) throws ParseException, InterruptedException {
        HashMap<String, Object> map = new HashMap<>();

        Transaction transaction = new Transaction();
        transaction.setId(0);
        transaction.setTransactionDate(new Date());
        transaction.setUserId((Integer) payload.get("user_id"));
        transaction.setNominal((Integer) payload.get("nominal"));
        transaction.setType((Integer) payload.get("type"));

        Integer balance = transactionService.getCurrentBalance();

        if (transaction.getNominal() > balance && (transaction.getType() == 2 || transaction.getType() == 4)) {
            map.put("success", false);
            map.put("message", "Balance is not enough");
            map.put("data", null);

            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }


        transactionService.createTransaction(transaction);

        User user = userService.findById(transaction.getUserId());
        transaction.setUser(user);

        String t = "";
        switch ((Integer) payload.get("type")) {
            case 2:
                t = "Meminjam uang";
                break;
            case 3:
                t = "Mengembalikan uang";
                break;
            case 4:
                t = "Mengambil uang";
                break;
            default:
                t = "Menyimpan uang";
        }

        MessageProducer producer = context.getBean(MessageProducer.class);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        String log = user.getName() + " " + t + " sebanyak " + kursIndonesia.format(transaction.getNominal());
        producer.sendMessage(log);

        map.put("success", true);
        map.put("message", "Success create transaction");
        map.put("data", transaction);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(required = false) String user_name
    ) throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        LocalDate today = LocalDate.now();

        String start = start_date, end = end_date;

        if (start == null || start.isEmpty() || end == null || end.isEmpty()) {
            start = today.minusDays(30).toString();
            end = today.toString();
        }

        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start + " 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end + " 23:59:59");

        Query query = new Query();
        query.addCriteria(Criteria.where("history_date").lte(endDate).gte(startDate));

        if (user_name != null) {
            query.addCriteria(Criteria.where("log").regex(toLikeRegex(user_name)));
        }
        query.with(Sort.by(Sort.Order.desc("history_date")));

        List<History> histories = mongoTemplate.find(query, History.class);

        map.put("success", true);
        map.put("message", "List History");
        map.put("data", histories);

        return ResponseEntity.ok(map);
    }

    private String toLikeRegex(String source) {
        return source.replaceAll("\\*", ".*");
    }

    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }

    @Bean
    MessageConsumer messageConsumer() {
        return new MessageConsumer();
    }

}

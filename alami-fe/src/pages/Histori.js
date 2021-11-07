/*
 * File: Histori.js
 * Project: alami-fe
 * File Created: Sunday, 7th November 2021 6:10:30 pm
 * Author: Ananda Yudhistira (anandabayu12@gmail.com)
 * -----
 * Last Modified: Sunday, 7th November 2021 7:19:12 pm
 * Modified By: Ananda Yudhistira (anandabayu12@gmail.com>)
 * -----
 * Copyright 2021 Ananda Yudhistira, -
 */
import { useEffect, useState } from 'react';
import {
  Row,
  Col,
  Container,
  Table,
  Form,
  FormGroup,
  Input,
  Button,
} from 'reactstrap';

import Moment from 'moment';
import idLocale from 'moment/locale/id';
import NumberFormat from 'react-number-format';
import Swal from 'sweetalert2';

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

import axios from '../config/axios';

export default function Histori() {
  const [anggota, setAnggota] = useState([]);
  const [histori, setHistori] = useState([]);

  const [filterName, setFilterName] = useState(0);

  var today = new Date();
  const [startDate, setStartDate] = useState(
    new Date().setDate(today.getDate() - 30)
  );
  const [endDate, setEndDate] = useState(new Date());

  useEffect(() => {
    Moment.locale('id', idLocale);
    getData();
  }, []);

  const getData = async (params) => {
    var user = await axios.get(`/users`);
    var tr = await axios.get(params ? params : `/history`);
    setAnggota(user.data.data);
    setHistori(tr.data.data);
  };

  const filter = async () => {
    let start = Moment(startDate).format('yyyy-MM-DD');
    let end = Moment(endDate).format('yyyy-MM-DD');
    var url = `/history?start_date=${start}&end_date=${end}`;

    if (filterName !== 0) url = url + `&user_name=${filterName}`;

    getData(url);
  };

  return (
    <Container className="mt-5">
      <h6 className="heading-small mb-4">List Histori</h6>

      <Form>
        <Row>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Nama</label>
              <Input
                onChange={(e) => {
                  setFilterName(e.target.value);
                }}
                type="select"
                value={filterName}
              >
                <option key={'asdw'} value={0}>
                  Semua Anggota
                </option>
                {anggota.map((v, i) => {
                  return (
                    <>
                      <option key={`${i}asf`} value={v.name}>
                        {v.name}
                      </option>
                    </>
                  );
                })}
              </Input>
            </FormGroup>
          </Col>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Tanggal Mulai</label>
              <DatePicker
                selected={startDate}
                onChange={(date) => setStartDate(date)}
                className="form-control"
                maxDate={endDate}
              />
            </FormGroup>
          </Col>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Type</label>
              <DatePicker
                selected={endDate}
                onChange={(date) => setEndDate(date)}
                className="form-control"
                minDate={startDate}
              />
            </FormGroup>
          </Col>
        </Row>
        <Row>
          <Col className="text-right" xs="6">
            <Button color="success" size="md" onClick={filter}>
              Filter
            </Button>
          </Col>
        </Row>
      </Form>

      <Row className="mt-3">
        <Col md>
          <Table bordered>
            <thead>
              <tr>
                <th>#</th>
                <th>Tanggal Transaksi</th>
                <th>Log</th>
              </tr>
            </thead>
            <tbody>
              {histori.map((v, i) => {
                return (
                  <tr key={`${i}tt`}>
                    <td className="text-center">{i + 1}</td>
                    <td
                      className={
                        v.log.includes('Menyimpan') ||
                        v.log.includes('Mengembalikan')
                          ? 'text-success'
                          : 'text-danger'
                      }
                    >
                      {v.log}
                    </td>
                    <td>
                      {Moment(v.history_date).format('DD MMMM yyyy HH:mm')}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
}

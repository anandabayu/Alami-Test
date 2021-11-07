/*
 * File: Transaksi.js
 * Project: alami-fe
 * File Created: Sunday, 7th November 2021 5:11:22 pm
 * Author: Ananda Yudhistira (anandabayu12@gmail.com)
 * -----
 * Last Modified: Sunday, 7th November 2021 7:19:15 pm
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

export default function Transaksi() {
  const [anggota, setAnggota] = useState([]);
  const [transaksi, setTransaksi] = useState([]);
  const [balance, setBalance] = useState(0);

  const [nama, setNama] = useState(0);
  const [nominal, setNominal] = useState(100000);
  const [type, setType] = useState(0);

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
    var tr = await axios.get(params ? params : `/transactions`);
    setAnggota(user.data.data);
    setTransaksi(tr.data.data);
    setBalance(tr.data.balance);
  };

  const submit = async () => {
    if (nama === 0 || nominal < 100000 || type === 0)
      return Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Form belum lengkap!',
      });

    const data = JSON.stringify({
      user_id: +nama,
      nominal: +nominal,
      type: +type,
    });
    try {
      let response = await axios.post(`/transactions`, data);

      getData();
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: response.data.message,
      });
      setNama(0);
      setNominal(100000);
      setType(0);
    } catch (err) {
      let message = err.response.data.message;
      return Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message ? message : err.message,
      });
    }
  };

  const filter = async () => {
    let start = Moment(startDate).format('yyyy-MM-DD');
    let end = Moment(endDate).format('yyyy-MM-DD');
    var url = `/transactions?start_date=${start}&end_date=${end}`;

    if (filterName !== 0) url = url + `&user_id=${+filterName}`;

    getData(url);
  };

  return (
    <Container className="mt-5">
      <h6 className="heading-small mb-4">Tambah Transaksi</h6>
      <Form>
        <Row>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Nama</label>
              <Input
                onChange={(e) => {
                  setNama(e.target.value);
                }}
                type="select"
                value={nama}
              >
                <option key={'asdf'} value={0}>
                  Semua Anggota
                </option>
                {anggota.map((v, i) => {
                  return (
                    <>
                      <option key={`${i}ca`} value={v.id}>
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
              <label className="form-control-label">Nominal</label>
              <NumberFormat
                value={nominal}
                className="form-control"
                placeholder="Rp. xxx.xxx,xx"
                thousandSeparator="."
                decimalSeparator=","
                prefix={'Rp. '}
                onValueChange={(values) => {
                  const { value } = values;
                  setNominal(value);
                }}
              />
            </FormGroup>
          </Col>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Type</label>
              <Input
                onChange={(e) => {
                  setType(e.target.value);
                }}
                type="select"
                value={type}
              >
                <option key="0f" value={0}>
                  Pilih Tipe
                </option>
                <option key="1f" value={1}>
                  Menyimpan
                </option>
                <option key="2f" value={2}>
                  Meminjam
                </option>
                <option key="3f" value={3}>
                  Mengembalikan
                </option>
                <option key="4f" value={4}>
                  Mengambil
                </option>
              </Input>
            </FormGroup>
          </Col>
        </Row>
        <Row>
          <Col className="text-right" xs="6">
            <Button color="success" size="md" onClick={submit}>
              Tambah
            </Button>
          </Col>
        </Row>
      </Form>

      <hr className="my-4" />
      <h6 className="heading-small mb-4">List Transaksi</h6>
      <h6 className="heading-small mb-4">
        Jumlah Uang saat ini:{' '}
        <NumberFormat
          value={balance}
          displayType={'text'}
          thousandSeparator={'.'}
          decimalSeparator={','}
          prefix={'Rp. '}
        />{' '}
      </h6>

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
                      <option key={`${i}asf`} value={v.id}>
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
                <th>Nama</th>
                <th>Tanggal Transaksi</th>
                <th>Tipe</th>
                <th>Nominal</th>
              </tr>
            </thead>
            <tbody>
              {transaksi.map((v, i) => {
                return (
                  <tr key={`${i}tt`}>
                    <td className="text-center">{i + 1}</td>
                    <td>{v.user.name}</td>
                    <td>
                      {Moment(v.transactionDate).format('DD MMMM yyyy HH:mm')}
                    </td>
                    <td>
                      {(() => {
                        switch (v.type) {
                          case 2:
                            return 'Meminjam';
                          case 3:
                            return 'Mengembalikan';
                          case 4:
                            return 'Mengambil';
                          default:
                            return 'Menyimpan';
                        }
                      })()}
                    </td>
                    <td>
                      <NumberFormat
                        className={(() => {
                          switch (v.type) {
                            case 2:
                              return 'text-danger';
                            case 3:
                              return 'text-success';
                            case 4:
                              return 'text-danger';
                            default:
                              return 'text-success';
                          }
                        })()}
                        value={v.nominal}
                        displayType={'text'}
                        thousandSeparator={'.'}
                        decimalSeparator={','}
                        prefix={(() => {
                          switch (v.type) {
                            case 2:
                              return '-Rp. ';
                            case 3:
                              return 'Rp. ';
                            case 4:
                              return '-Rp. ';
                            default:
                              return 'Rp. ';
                          }
                        })()}
                      />{' '}
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

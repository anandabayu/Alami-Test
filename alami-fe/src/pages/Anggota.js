/*
 * File: Anggota.js
 * Project: alami-fe
 * File Created: Sunday, 7th November 2021 4:14:28 pm
 * Author: Ananda Yudhistira (anandabayu12@gmail.com)
 * -----
 * Last Modified: Monday, 8th November 2021 11:36:28 am
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
import Swal from 'sweetalert2';

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

import axios from '../config/axios';

export default function Anggota() {
  const [anggota, setAnggota] = useState([]);

  const [nama, setNama] = useState('');
  const [tanggalLahir, setTanggalLahir] = useState(new Date());
  const [alamat, setAlamat] = useState('');

  useEffect(() => {
    Moment.locale('id', idLocale);
    getData();
  }, []);

  const getData = async () => {
    var response = await axios.get(`/users`);
    setAnggota(response.data.data);
  };

  const submit = async () => {
    if (!nama || !alamat || !tanggalLahir)
      return Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Form belum lengkap!',
      });

    const user = JSON.stringify({
      name: nama,
      borndate: tanggalLahir,
      address: alamat,
    });
    try {
      let response = await axios.post(`/users`, user);

      getData();
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: response.data.message,
      });
      setNama('');
      setTanggalLahir(new Date());
      setAlamat('');
    } catch (err) {
      let message = err.response.data.message;
      return Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message ? message : err.message,
      });
    }
  };

  return (
    <Container className="mt-5">
      <h6 className="heading-small mb-4">Tambah Anggota</h6>
      <Form>
        <Row>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Nama</label>
              <Input
                value={nama || ''}
                onChange={(e) => setNama(e.target.value)}
                placeholder="Nama"
                type="text"
              />
            </FormGroup>
          </Col>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Tanggal Lahir</label>
              <DatePicker
                selected={tanggalLahir}
                onChange={(date) => setTanggalLahir(date)}
                className="form-control"
              />
            </FormGroup>
          </Col>
          <Col md="4">
            <FormGroup>
              <label className="form-control-label">Alamat</label>
              <Input
                value={alamat || ''}
                onChange={(e) => setAlamat(e.target.value)}
                placeholder="Alamat"
                type="text"
              />
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
      <h6 className="heading-small mb-4">List Anggota</h6>
      <Row className="mt-3">
        <Col md>
          <Table bordered>
            <thead>
              <tr>
                <th>#</th>
                <th>Nama</th>
                <th>Tanggal Lahir</th>
                <th>Alamat</th>
              </tr>
            </thead>
            <tbody>
              {anggota.map((v, i) => {
                return (
                  <tr key={`${i}ta`}>
                    <td className="text-center">{i + 1}</td>
                    <td>{v.name}</td>
                    <td>{Moment(v.borndate).format('DD MMMM yyyy')}</td>
                    <td>{v.address}</td>
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

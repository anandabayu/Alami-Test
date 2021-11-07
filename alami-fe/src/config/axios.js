/*
 * File: axios.js
 * Project: alami-fe
 * File Created: Sunday, 7th November 2021 5:05:32 pm
 * Author: Ananda Yudhistira (anandabayu12@gmail.com)
 * -----
 * Last Modified: Sunday, 7th November 2021 5:06:59 pm
 * Modified By: Ananda Yudhistira (anandabayu12@gmail.com>)
 * -----
 * Copyright 2021 Ananda Yudhistira, -
 */
import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://127.0.0.1:8080/api',
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json; charset=utf-8',
  },
});

export default instance;

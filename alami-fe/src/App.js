import { Container } from 'reactstrap';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AppNavbar from './components/AppNavbar';
import Anggota from './pages/Anggota';
import Transaksi from './pages/Transaksi';
import Histori from './pages/Histori';

function App() {
  return (
    <BrowserRouter>
      <Container>
        <AppNavbar />
        <Routes>
          <Route path="/histori" element={<Histori />} />
          <Route path="/transaksi" element={<Transaksi />} />
          <Route path="/" element={<Anggota />} />
        </Routes>
      </Container>
    </BrowserRouter>
  );
}

export default App;

import { Navbar, NavItem, Nav, NavLink, Collapse } from 'reactstrap';
import { Link } from 'react-router-dom';
export default function AppNavbar() {
  return (
    <>
      <Navbar color="light" expand="md" light>
        <Collapse navbar>
          <Nav className="me-auto" navbar>
            <NavItem>
              <NavLink>
                <Link to="/">Anggota</Link>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink>
                <Link to="/transaksi">Transaksi</Link>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink>
                <Link to="/histori">Histori</Link>
              </NavLink>
            </NavItem>
          </Nav>
        </Collapse>
      </Navbar>
    </>
  );
}

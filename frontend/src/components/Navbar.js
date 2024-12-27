import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import '../style/css/Navbar.css';

function NavBar() {
  const [click, setClick] = useState(false);

  const handleClick = () => setClick(!click);
  return (
    <>
      <nav className="navbar">
        <div className="nav-container">
          <NavLink exact to="/" className="nav-logo">
            <span>CoronaTracker</span>
          </NavLink>

          <ul className={click ? "nav-menu active" : "nav-menu"}>
            <li className="nav-item">
              <NavLink
                exact
                to="/"
                activeClassName="active"
                className="nav-links"
                onClick={handleClick}
              >
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                exact
                to="/api/news/add"
                activeClassName="active"
                className="nav-links"
                onClick={handleClick}
              >
                Add News
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                exact
                to="/api/news/reports"
                activeClassName="active"
                className="nav-links"
                onClick={handleClick}
              >
                Reports
              </NavLink>
            </li>
          </ul>
        </div>
      </nav>
    </>
  );
}

export default NavBar;
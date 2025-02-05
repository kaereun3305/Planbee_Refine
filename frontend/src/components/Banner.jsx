import React from "react";
import logoYellow from "../images/Logo_Yellow.png";
import "../css/Banner.css";

const Banner = () => {
  return (
    <div className="banner_container">
      <div className="banner_logo">
        <img src={logoYellow} alt="Login Page Logo" />
      </div>
      <div className="banner_btn">
        <div className="banner_username">반갑습니다 일형님!</div>
        <button className="banner_logout">Logout</button>
      </div>
    </div>
  );
};

export default Banner;

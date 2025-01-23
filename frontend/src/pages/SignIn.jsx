import React, { useState } from "react";
import PopUp from "../components/PopUp";
import "../css/SignIn.css";
import "../css/SignUp.css";

const SignIn = () => {
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const togglePopup = () => {
    setIsPopupOpen((prev) => !prev);
  };

  return (
    <div className="login_container">
      <div className="login_boxL">Logo</div>
      <div className="login_boxR">
        <div className="login_logo">Logo</div>
        <div className="login_input">
          <form>
            <div className="form_group">
              <input
                className="login_text"
                name="username"
                type="text"
                placeholder="아이디"
              />
            </div>
            <div className="form_group">
              <input
                className="login_text"
                name="password"
                type="password"
                placeholder="비밀번호"
              />
            </div>

            <div className="form_btn">
              <div className="logIn_button">
                <input className="login_btn" type="submit" value="Login" />
              </div>
              <button
                className="login_btn"
                onClick={(e) => {
                  e.preventDefault();
                  togglePopup();
                }}
              >
                SignUp
              </button>
              <PopUp isOpen={isPopupOpen} onClose={togglePopup}>
                <div className="signup_container">
                  <div className="signup_logo">logo</div>
                  <form>
                    <div className="signup_form">
                      <input
                        type="text"
                        id="userId"
                        name="userId"
                        placeholder="User ID"
                      />
                      <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Password"
                      />
                      <input
                        type="password"
                        id="confirmPassword"
                        name="confirmPassword"
                        placeholder="Confirm Password"
                      />
                      <input
                        type="text"
                        id="userName"
                        name="userName"
                        placeholder="User Name"
                      />
                      <div className="email_certificate">
                        <input
                          type="email"
                          id="email"
                          name="email"
                          placeholder="Email"
                        />
                        <button type="button" className="signup_btn1">
                          Send Code
                        </button>
                      </div>
                      <div className="email_certificate">
                        <input
                          type="text"
                          id="verificationCode"
                          name="verificationCode"
                          placeholder="Verification Code"
                        />
                        <button type="button" className="signup_btn2">
                          Complete
                        </button>
                      </div>
                      <input
                        type="tel"
                        id="phone"
                        name="phone"
                        placeholder="010-1234-5678"
                      />
                    </div>
                    <div className="signup_button">
                      <input
                        className="signup_btn"
                        type="submit"
                        value="SignUp"
                      />
                    </div>
                  </form>
                </div>
              </PopUp>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignIn;

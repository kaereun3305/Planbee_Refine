import React from "react";
import "../css/SignIn.css";

const signIn = () => {
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
              <button className="login_btn">SignUp</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default signIn;

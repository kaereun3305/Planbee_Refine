import React, { Children } from "react";
import "../css/PopUp.css";
import { Icon } from "@iconify/react";

const PopUp = ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;

  return (
    <div className="popup_overlay" onClick={onClose}>
      <div className="popup" onClick={(e) => e.stopPropagation()}>
        <button className="popup_close_btn" onClick={onClose}>
          <Icon
            icon="si:close-circle-fill"
            width="28px"
            height="28px"
            style={{ color: "#ffd438" }}
          />
        </button>
        {children}
      </div>
    </div>
  );
};

export default PopUp;

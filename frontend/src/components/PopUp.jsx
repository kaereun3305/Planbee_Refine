import React, { Children } from "react";
import "../css/PopUp.css";

const PopUp = ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;

  return (
    <div className="popup_overlay" onClick={onClose}>
      <div className="popup" onClick={(e) => e.stopPropagation()}>
        <button className="popup_close_btn" onClick={onClose}>
          Close
        </button>
        {children}
      </div>
    </div>
  );
};

export default PopUp;

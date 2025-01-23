import React from "react";
import { NavLink } from "react-router-dom";
import "../css/SideBar.css";

const SideBar = () => {
  return (
    <nav className="sidebar_container">
      <NavLink
        className="sidebar_menu"
        to={"/todolist"}
        style={({ isActive }) => {
          return {
            backgroundColor: isActive ? "#EFEFEF" : "#FEFCFC",
          };
        }}
      >
        ToDoList
      </NavLink>
      <NavLink
        className="sidebar_menu"
        to={"/archive"}
        style={({ isActive }) => {
          return {
            backgroundColor: isActive ? "#EFEFEF" : "#FEFCFC",
          };
        }}
      >
        Archive
      </NavLink>
      <NavLink
        className="sidebar_menu"
        to={"/calendar"}
        style={({ isActive }) => {
          return {
            backgroundColor: isActive ? "#EFEFEF" : "#FEFCFC",
          };
        }}
      >
        Calendar
      </NavLink>
      <NavLink
        className="sidebar_menu"
        to={"/social"}
        style={({ isActive }) => {
          return {
            backgroundColor: isActive ? "#EFEFEF" : "#FEFCFC",
          };
        }}
      >
        Social
      </NavLink>
    </nav>
  );
};

export default SideBar;

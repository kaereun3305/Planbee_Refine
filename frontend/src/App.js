import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SignIn from "./pages/SignIn";
import ToDoList from "./pages/ToDoList";
import Archive from "./pages/Archive";
import Callendar from "./pages/Callendar";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<ToDoList />} />
        {/* 
            <Route path="/" element={<SignIn />} />
            <Route path="/todolist" element={<ToDoList />} />
            <Route path="/archive" element={<Archive />} />
            <Route path="/callendar" element={<Callendar/>} /> 
        */}
      </Routes>
    </Router>
  );
}

export default App;

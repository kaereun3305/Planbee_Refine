import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SignIn from "./pages/SignIn";
import ToDoList from "./pages/ToDoList";
import Archive from "./pages/Archive";
import Calendar from "./pages/Calendar";

function App() {
  return (
    <Router>
      <Routes>
        {/*<Route path="/" element={<SignIn />} />*/}
        <Route path="/" element={<ToDoList />} />
        <Route path="/todolist" element={<ToDoList />} />
        <Route path="/archive" element={<Archive />} />
        <Route path="/calendar" element={<Calendar />} />
        <Route path="/login" element={<SignIn />} />
      </Routes>
    </Router>
  );
}

export default App;

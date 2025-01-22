import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SignIn from "./pages/SignIn";
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SignIn />} />
        {/* <Route path="/" element={< />} />
        <Route path="/" element={< />} />
        <Route path="/" element={< />} />
        <Route path="/" element={</>} />
        <Route path="/" element={</>} /> */}
      </Routes>
    </Router>
  );
}

export default App;

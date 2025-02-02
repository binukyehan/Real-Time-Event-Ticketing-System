import React from "react";
import CreateEvent from "./components/event/CreateEvent.jsx";
import DeleteEvent from "./components/event/DeleteEvent.jsx";
import IssueTickets from "./components/vendor/IssueTickets.jsx";
import PurchaseTickets from "./components/customer/PurchaseTickets.jsx";
import MainPage from "./pages/MainPage.jsx";
import AdminPage from "./pages/AdminPage.jsx";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import VendorLogin from "./components/vendor/LoginVendor.jsx";
import EventList from "./pages/EventList.jsx";
import CustomerLogin from "./components/customer/LoginCustomer.jsx";
import CustomerRegister from "./components/customer/RegisterCustomer.jsx";
import VendorRegister from "./components/vendor/RegisterVendor.jsx";
import VendorDelete from "./components/vendor/DeleteAccountVendor.jsx";
import CustomerDelete from "./components/customer/DeleteAccountCustomer.jsx";
import VendorChangePw from "./components/vendor/ChangePasswordVendor.jsx";
import CustomerChangePw from "./components/customer/ChangePasswordCustomer.jsx";
import ControlEvent from "./components/event/ControlEvent.jsx";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/ws" element={<EventList />} />
        <Route path="/vendor-login" element={<VendorLogin />} />
        <Route path="/admin" element={<AdminPage />} />
        <Route path="/create-event" element={<CreateEvent />} />
        <Route path="/delete-event" element={<DeleteEvent />} />
        <Route path="/issue-tickets" element={<IssueTickets />} />
        <Route path="/purchase-tickets" element={<PurchaseTickets />} />
        <Route path="/customer-login" element={<CustomerLogin />} />
        <Route path="/customer-register" element={<CustomerRegister />} />
        <Route path="/vendor-register" element={<VendorRegister />} />
        <Route path="/vendor-delete-acc" element={<VendorDelete />} />
        <Route path="/customer-delete-acc" element={<CustomerDelete />} />
        <Route path="/vendor-change-pw" element={<VendorChangePw />} />
        <Route path="/customer-change-pw" element={<CustomerChangePw />} />
        <Route path="/control-panel" element={<ControlEvent />} />
      </Routes>
    </Router>
  );
};

export default App;

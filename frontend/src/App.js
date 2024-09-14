import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './components/login/Login';
import SignUp from './components/sign_up/SignUp';
import Dashboard from './components/dashboard/Dashboard';
import Pipe from './components/pipe/Pipe';
import PipeExecutionForm from './components/pipe/PipeExecutionForm';
import PipeSettings from './components/pipe/PipeSettings';
import PipeExecutionHistoryDetailsCard from './components/pipe/PipeExecutionHistoryDetailsCard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element= { <Login /> } />
        <Route path='/login' element= { <Login /> } />
        <Route path='/sign-up' element= { <SignUp /> } />
        <Route path='/dashboard' element= { <Dashboard /> } />
        <Route path='/pipe' element= { <Pipe /> } />
        <Route path='/execute' element= { <PipeExecutionForm />} />
        <Route path='/execution-details' element= { <PipeExecutionHistoryDetailsCard /> } />
        <Route path='/pipe-settings' element= { <PipeSettings /> } />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

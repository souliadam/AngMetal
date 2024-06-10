import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import LandingIntro from './LandingIntro';
import ErrorText from '../../components/Typography/ErrorText';

const Login = ({ auth, handleLogin }) => {
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    handleLogin().catch((error) => {
      setErrorMessage("Failed to login. Please try again.");
      setLoading(false);
    });
  };

  if (auth) {
    return <Navigate to="/callback" />;
  }

  return (
    <div className="min-h-screen bg-base-200 flex items-center">
      <div className="card mx-auto w-full max-w-5xl shadow-xl">
        <div className="grid md:grid-cols-2 grid-cols-1 bg-base-100 rounded-xl">
          <div className=''>
            <LandingIntro />
          </div>
          <div className='py-24 px-10'>
            <h2 className='text-2xl font-semibold mb-2 text-center'>Login</h2>
            <form onSubmit={handleSubmit}>
              {errorMessage && <ErrorText styleClass="mt-8">{errorMessage}</ErrorText>}
              <button type="submit" className={"btn mt-2 w-full btn-primary" + (loading ? " loading" : "")}>
                {loading ? "Logging in..." : "Login"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;

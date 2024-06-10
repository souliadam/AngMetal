import React, { useEffect, useState } from "react";
import "./App.css";
import { createZitadelAuth } from "@zitadel/react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Login from "./features/user/Login";
import Callback from "./components/Callback";

function App() {
  const config = {
    authority: "https://security-bzvymn.zitadel.cloud",
    client_id: "270265983627815536@angmetal-front",
  };

  const zitadel = createZitadelAuth(config);

  function login() {
    zitadel.authorize();
  }

  function signout() {
    zitadel.signout();
  }

  const [authenticated, setAuthenticated] = useState(null);

  useEffect(() => {
    zitadel.userManager.getUser().then((user) => {
      if (user) {
        setAuthenticated(true);
      } else {
        setAuthenticated(false);
      }
    });
  }, [zitadel]);

  return (
    React.createElement("div", { className: "App" },
          React.createElement(BrowserRouter, null,
          React.createElement(Routes, null,
            React.createElement(Route, {
              path: "/",
              element: React.createElement(Login, { authenticated: authenticated, handleLogin: login })
            }),
            React.createElement(Route, {
              path: "/callback",
              element: React.createElement(Callback, {
                authenticated: authenticated,
                setAuth: setAuthenticated,
                handleLogout: signout,
                userManager: zitadel.userManager
              })
            })
          )
        )
      )
    )
}

export default App;

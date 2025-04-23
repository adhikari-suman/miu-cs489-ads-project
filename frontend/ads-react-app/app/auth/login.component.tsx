import { useState } from "react";
import authService from "./auth.service";
import { useNavigate } from "react-router";
import { LocalStorageKeys } from "~/core/constants";

export default function Login() {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const navigate = useNavigate();

  localStorage.removeItem(LocalStorageKeys.JWT_TOKEN_KEY);

  const onUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const onPasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const onLoginPressed = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    authService.loginUser(username, password).then((isUserLoggedIn) => {
      if (isUserLoggedIn) {
        navigate("/", { replace: true });
      } else {
        alert("Invalid username or password!");
      }
    });
  };

  return (
    <form id="login-form">
      <input
        type="text"
        id="username"
        value={username}
        placeholder="Username"
        onChange={onUsernameChange}
      />
      <br />
      <input
        type="password"
        id="password"
        value={password}
        placeholder="password"
        onChange={onPasswordChange}
      />
      <br />
      <button type="submit" onClick={onLoginPressed}>
        Login
      </button>
    </form>
  );
}

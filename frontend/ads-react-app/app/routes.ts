import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
  index("home/home.component.tsx"),
  route("/login", "auth/login.component.tsx"),
  route("/register", "auth/register.component.tsx"),
] satisfies RouteConfig;

import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    index("home/home.tsx"),
    route("/login", "auth/login.tsx"),
    route("/register", "auth/register.tsx"),
] satisfies RouteConfig;

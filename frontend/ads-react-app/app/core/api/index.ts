interface AuthRoutes {
  login: string;
  register: string;
}

interface ApiRoutes {
  auth: AuthRoutes;
}

const apiRoutes = Object.freeze({
  auth: {
    login: "/auth/authenticate",
    register: "/auth/register",
  },
});

export default apiRoutes;

export const isValidJwtToken = (token: string | null): boolean => {
    let isTokenValid = typeof token === 'string' && token.trim() !== '';

    if (!isTokenValid) {
        return false;
    }


}
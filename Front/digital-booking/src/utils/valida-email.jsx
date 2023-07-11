    export const validaEmail = (email) => {
        const regex = new RegExp(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]+(\.[a-z]+)?$/,'i');
        return regex.test(email);
    }

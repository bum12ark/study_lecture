const tag = '[UserModel]'

export default {
    join(user) {
        console.log(tag, 'join()', JSON.stringify(user))
        return fetch("/user/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
    },

    checkEmail(email) {
        console.log(tag, 'checkEmail', email)
        return fetch("/user/" + email)
    },

    login(user) {
        console.log(tag, 'login()', JSON.stringify(user))
        return fetch("/user/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
    }
}
const tag = '[UserModel]'

export default {
    join(user) {
        console.log(tag, 'join()', JSON.stringify(user))
        /*
        return new Promise((resolve, reject) => {
            // 추후 순수 JS 형식으로 변경할 지 의논 해봐야함
            $.ajax({
                type: "POST",
                url: "/user/",
                data: JSON.stringify(user),
                contentType: "application/json", // requestBody를 위해 있어야함
                dataType: "json",
                success: function (data) {
                    resolve(data)
                }, error: function (request,status,error) {
                    reject("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
                }
            })
        })
        */
        return fetch("/user/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
    }
}
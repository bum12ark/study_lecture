import View from './View.js'

const tag = '[JoinView]'

const JoinView = Object.create(View)

JoinView.setup = function (el) {
    this.init(el)
    this.joinEmail = el.querySelector('#join-email')
    this.joinUsername = el.querySelector('#join-name')
    this.joinPassword = el.querySelector('#join-password')

    this.checkEmail = el.querySelector('#check-email')
    this.submitJoin = el.querySelector('#join')

    this.bindEvent()
    return this
}

JoinView.bindEvent = function () {
    this.on('submit', e => e.preventDefault())
    this.submitJoin.addEventListener('click', e => this.onClickJoin())
    this.checkEmail.addEventListener('click', e => this.onClickCheckEmail())
}

JoinView.onClickJoin = function () {
    if (!this.el.checkValidity()) return
    const user = {
        email: this.joinEmail.value,
        name: this.joinUsername.value,
        password: this.joinPassword.value
    }
    this.emit('@join', {input: user})
}

JoinView.onClickCheckEmail = function () {
    console.log(tag, 'onClickCheckEmail')
    if (!this.joinEmail.checkValidity()) {
        alert("이메일을 확인해 주세요.")
        return
    }
    this.emit('@check-email', {input: this.joinEmail.value})
}

export default JoinView
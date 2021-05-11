import View from './View.js'

const tag = '[JoinView]'

const JoinView = Object.create(View)

JoinView.setup = function (el) {
    this.init(el)
    this.joinEmail = el.querySelector('#join-email')
    this.joinUsername = el.querySelector('#join-username')
    this.joinPassword = el.querySelector('#join-password')
    this.submitJoin = el.querySelector('#btn-join')

    this.bindEvent()
    return this
}

JoinView.bindEvent = function () {
    this.on('submit', e => e.preventDefault())
    this.submitJoin.addEventListener('click', e => this.onClickJoin())
}

JoinView.onClickJoin = function () {
    var user = {
        email: this.joinEmail.value,
        name: this.joinUsername.value,
        password: this.joinPassword.value
    }
    this.emit('@join', {input: user})
}

export default JoinView
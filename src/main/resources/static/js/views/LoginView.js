import View from './View.js'

const tag = '[LoginView]'

const LoginView = Object.create(View)

LoginView.setup = function (el) {
  console.log(tag, 'setup()', el)
  this.init(el)
  this.loginEmail = el.querySelector('#login-email')
  this.loginPassword = el.querySelector('#login-password')
  this.submitLogin = el.querySelector('#login')

  this.bindEvent()

  return this
}

LoginView.bindEvent = function () {
  this.on('submit', e => e.preventDefault())
  this.submitLogin.addEventListener('click', e => this.onClickLogin())
}

LoginView.onClickLogin = function () {
  if (!this.el.checkValidity()) return
  const user = {
    email: this.loginEmail.value,
    password: this.loginPassword.value
  }
  this.emit('@login', {input: user})
}

export default LoginView
import JoinView from '../views/JoinView.js'
import LoginView from "../views/LoginView.js";

import UserModel from '../models/UserModel.js'

const tag = '[UserController]'

export default {
  init() {
    console.log(tag, 'init()')

    if (document.querySelector('#join-form')) {
      JoinView.setup(document.querySelector('#join-form'))
        .on('@join', e => this.onJoinSubmit(e.detail.input))
        .on('@check-email', e => this.onCheckEmailSubmit(e.detail.input))
    } else if (document.querySelector('#login-form')) {
      LoginView.setup(document.querySelector('#login-form'))
        .on('@login', e => this.onLoginSubmit(e.detail.input))
    }

  },

  onJoinSubmit(input) {
    console.log(tag, 'onJoinSubmit', input)
    UserModel.join(input)
      // TODO 이 부분에 대해 이야기 진행 해봐야함 (어떤식으로 구현할 지)
      .then(response => this.onJoinResult(response))
      .then(data => console.log(tag, 'data', data))
      .catch(error => console.log(tag, 'UserModel.join', error))
  },

  onJoinResult(response) {
    console.log(tag, 'onJoinResult', response)
    if (response.status == '201') {
      alert("계정 생성에 성공하였습니다.\n메인 페이지로 이동합니다.")
      location.href = '/'
    } else if (response.status == '400') {
      alert("회원가입 입력 양식이 잘못되었습니다.")
    } else {
      alert(response)
    }

    return response.json()
  },

  onCheckEmailSubmit(input) {
    console.log(tag, 'onCheckEmailSubmit', input)
    UserModel.checkEmail(input)
      .then(response => this.onCheckEmailResult(response))
      .catch(error => console.log(tag, 'UserModel.checkEmail', error)) // 네트워크 장애나 요청이 완료되지 못한 상태에는 reject가 반환됩니다.
  },

  onCheckEmailResult(response) {
    // TODO 이메일 인증 완료 후 버튼 처리 -> ResultView.js 로 이관
    if (response.status == '404') {
      alert("사용 가능한 이메일 입니다.")
      document.querySelector('#join').disabled = false
    } else if (response.status == '200') {
      alert("사용 중인 이메일 입니다.")
      document.querySelector('#join').disabled = true
    } else {
      alert(response)
    }
  },

  onLoginSubmit(input) {
    console.log(tag, 'onLoginSubmit', input)
    UserModel.login(input)
      .then(response => response.json())
      .then(data => this.onLoginResult(data))
      .catch(error => console.log('catch()', error)) // 네트워크 장애나 요청이 완료되지 못한 상태에는 reject가 반환됩니다.
  },

  onLoginResult(data) {
    if (data.status == "OK") {
      alert("로그인에 성공하였습니다.\n메인 페이지로 이동합니다.")
      location.href = '/'
    } else if (data.status == "ALERT") {
      alert(data.message)
    }
  }

}
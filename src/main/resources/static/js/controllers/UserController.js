import JoinView from '../views/JoinView.js'

import UserModel from '../models/UserModel.js'

const tag = '[UserController]'

export default {
  init() {
    console.log(tag, 'init()')

    JoinView.setup(document.querySelector('form'))
      .on('@join', e => this.onJoinSubmit(e.detail.input))
  },

  onJoinSubmit(input) {
    console.log(tag, 'onJoinSubmit', input)
    UserModel.join(input)
      .then(response => response.json())
      .then(data => this.onJoinResult(data))
      .catch(error => console.log(tag, 'UserModel.join', error))
  },

  onJoinResult(data) {
    console.log(tag, 'onJoinResult', data)
    if (data.status == "OK") {
      alert("계정 생성에 성공하였습니다.\n메인 페이지로 이동합니다.")
      location.href = "/"
    } else {
      alert("게정 생성에 실패하였습니다.")
    }
  }

}
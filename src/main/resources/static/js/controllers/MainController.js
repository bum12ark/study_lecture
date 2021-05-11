import JoinView from '../views/JoinView.js'

const tag = '[MainController]'

export default {
    init() {
        console.log(tag, 'init()')

        JoinView.setup(document.querySelector('form'))
    }
}
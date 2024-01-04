const dom = document.querySelector('.title')
const data = 'What Is Your Nickname?'.split('')
const writing=(index)=> {
        if (index < data.length) {
            dom.innerHTML += data[index]
            setTimeout(writing.bind(this), 80, ++index)
        }
    }
writing(0);
var Generator = require('yeoman-generator');

module.exports = class extends Generator {
    constructor(args, opts) {
        super(args, opts);

        this.option('run');
    }

    async prompting() {
        this.answers = await this.prompt([{
            type: 'input',
            name: 'mod',
            message: 'Your project mod name',
            default: "testgo"
        }]);
    }

    writing() {
        this.fs.copyTpl(
            this.templatePath('main.go'),
            this.destinationPath('main.go')
        );
    }

    install() {
        this.spawnCommand('go', ['mod', 'init', this.answers.mod]);
    }

    async end() {
        this.spawnCommand('go', ['run', 'main.go']);
        this.log('init project finished!');

        const answers = await this.prompt([{
            type: 'confirm', // https://github.com/SBoudrias/Inquirer.js/blob/master/README.md#prompt
            name: 'edit',
            message: 'edit',
            default: true
        }]);
        this.log(answers.edit);
        if (answers.edit) {
            this.spawnCommand('code', ['.']);
        }
    }

    default() {
        //this.log('method1 just run');
    }
};
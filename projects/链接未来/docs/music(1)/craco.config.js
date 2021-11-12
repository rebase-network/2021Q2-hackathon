const CracoLessPlugin = require('craco-less');

module.exports = {
    plugins: [
        {
            plugin: CracoLessPlugin,
            options: {
                lessLoaderOptions: {
                    lessOptions: {
                        modifyVars: {
                            '@primary-color': '#1DA57A',
                            '@success-color': '#52c41a',
                            '@warning-color': '#faad14'

                        },
                        javascriptEnabled: true,
                    },
                },
            },
        },
    ],
};
'use strict';

const _ = require('lodash');

const widgetGlobalParams = {
    user: {
        id: String,
    },
    account: String,
    params: {
    },
    data: String
};

exports.setModelSchema = function (params) {
    let schema = _.cloneDeep(widgetGlobalParams);
    _.merge(schema, {params: params});
    return schema;
};
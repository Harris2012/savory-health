//module
var app = angular.module('app', ['ngResource', 'ui.router']);

//config
app.config(route);

//service
app.service('HealthService', ['$resource', '$q', HealthService]);


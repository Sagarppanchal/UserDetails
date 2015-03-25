
var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onCordovaInfoReady');
// Tell cordova channel to wait on the CordovaInfoReady event
channel.waitForInitialization('onCordovaInfoReady');

/**
 * It represents Android user;s primary email address and associated phone number.
 * @constructor
 */
function User() {
    this.number = null;
    this.email = null;

    var aUser = this;

    channel.onCordovaReady.subscribe(function() {
        aUser.getInfo(function(info) {
            aUser.number = info.number;
            aUser.email = info.email;
            channel.onCordovaInfoReady.fire();
        },function(e) {
            aUser.available = false;
            utils.alert("[ERROR] Error initializing Cordova: " + e);
        });
    });
}

/**
 * Get User info
 *
 * @param {Function} successCallback The function to call when the heading data is available
 * @param {Function} errorCallback The function to call when there is an error getting the heading data. (OPTIONAL)
 */
User.prototype.getInfo = function(successCallback, errorCallback) {
    alert("cammm");
    argscheck.checkArgs('fF', 'User.getInfo', arguments);
    exec(successCallback, errorCallback, "User", "getuserinfo", []);
};

module.exports = new User();



window.mpushnotification = function(task,args,success,error) {
	cordova.exec(success, error, "mPushNotification", task, args);
};

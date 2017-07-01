const Cookie = {
	create : function(key, value) {
        var expires = new Date();
        expires.setTime(expires.getTime() + (1 * 24 * 60 * 60 * 1000));
        document.cookie = key + '=' + value + ';expires=' + expires.toUTCString();
    },
    
	erase : function(key) {
        var expires = new Date();
        document.cookie = key + '=;expires=' + expires.toUTCString();
    },

    // Gets a cookie with a specified key
    read : function(key) {
        var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
        return keyValue ? keyValue[2] : null;
    },
}


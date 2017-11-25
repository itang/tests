local _M = {}
local mt = {__index = _M}

function _M:foo()
    return "<h1>default page (from ngx-lua)</h1><p>Hello, World</p>"
end

return _M

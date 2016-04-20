import msgpack

with open('t.bat', 'rb') as f:
    raw = f.read()
    print(msgpack.unpackb(raw))

class Test

  def self.vi(a, b="0", c="0")
    a.to_i * 10000 + b.to_i * 100.to_i + c.to_i
  end

  def self.version_to_number(s)
    begin
      a = s.split "."
      ret = 
      case a.length
      when 1
        vi a[0]
      when 2
        vi a[0], a[1]
      when 3
        vi a[0], a[1], a[2]
      else 
        -1
      end
    rescue e
      puts e
      -1
    end

    ret.tap {|it| puts it}
  end
end

Test.version_to_number("2.3.6")

Test.version_to_number("8.1") 

Test.version_to_number("1.0.0")

Test.version_to_number("8.1.0.1")

Test.version_to_number("8.1b")

Test.version_to_number("somerr")

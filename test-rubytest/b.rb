require 'minitest/autorun'

class Something < Minitest::Test
  def test_works
    assert_equal 10, 10
  end

  def test_really_works
    assert_equal 11, 11
  end
end
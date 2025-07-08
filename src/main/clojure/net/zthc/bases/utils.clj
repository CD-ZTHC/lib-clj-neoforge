(ns net.zthc.bases.utils
  (:require [clojure.string :as str]))


(defn kebab->snake
  "将 kebab-case 风格的字符串（例如 'my-string'）转换为 snake_case 风格（例如 'my_string'）。
  它通过将字符串中的所有连字符 '-' 替换为下划线 '_' 来实现。"
  [s]
  (str/replace s #"-" "_"))

(defn snake->kebab
  "将 snake_case 风格的字符串（例如 'my_string'）转换为 kebab-case 风格（例如 'my-string'）。
  它通过将字符串中的所有下划线 '_' 替换为连字符 '-' 来实现。"
  [s]
  (str/replace s #"_" "-"))

(defn camel->snake
  "将 camelCase 风格的字符串（例如 'myString' 或 'MyString'）转换为 snake_case 风格的初步形式。
  它会在小写字母/数字与大写字母之间插入下划线。
  例如：'myString' -> 'my_String'，'MyString' -> 'My_String'。
  注意：此函数仅插入下划线，通常需要后续步骤（如转换为全小写）来完成标准的 snake_case 转换。"
  [s]
  (str/replace s #"([a-z0-9])([A-Z])" "$1_$2"))

(defn ->snake-case
  "将输入字符串（可能为 kebab-case 或 camelCase 风格）转换为全小写的 snake_case 风格。
  处理顺序：
  1. kebab-case 转 snake_case (例如 'foo-bar' -> 'foo_bar')。
  2. camelCase 转 snake_case (例如 'fooBar' -> 'foo_Bar', 'FooBar' -> 'Foo_Bar')。
  3. 转换为全小写 (例如 'foo_Bar' -> 'foo_bar')。

  示例：
  'my-variable-NAME' -> 'my_variable_name'
  'myVariableName' -> 'my_variable_name'
  'MyVariableName' -> '_my_variable_name' (注意：若以大写开头且前面无小写/数字，camel->snake 可能产生前导下划线，然后 lower-case 处理)
  更准确地说，'MyVariableName' -> 'my_variable_name' 经过 kebab->snake (无变化) -> camel->snake ('My_Variable_Name') -> lower-case ('my_variable_name')"
  [k]
  (-> k
      str ; 确保输入是字符串，如果k是关键字，name函数会在->snake-case-keyword中处理
      kebab->snake
      camel->snake
      str/lower-case))

(defn ->snake-case-keyword
  "将 Clojure 关键字（可能为 kebab-case 或 camelCase 风格）转换为全小写的 snake_case 风格的关键字。
  例如：
  :my-keyword-NAME -> :my_keyword_name
  :myKeywordName -> :my_keyword_name"
  [k]
  (-> k
      name
      ->snake-case
      keyword))

(defn ->kebab
  "将输入字符串（可能为 snake_case 或 camelCase 风格）转换为全小写的 kebab-case 风格。
  处理顺序：
  1. camelCase 转 snake_case (例如 'fooBar' -> 'foo_Bar', 'FooBar' -> 'Foo_Bar')。
  2. 转换为全小写 (例如 'foo_Bar' -> 'foo_bar')。
  3. snake_case 转 kebab-case (例如 'foo_bar' -> 'foo-bar')。

  示例：
  'my_variable_NAME' -> 'my-variable-name'
  'myVariableName' -> 'my-variable-name'
  'MyVariableName' -> 'my-variable-name'"
  [k]
  (-> k
      ->snake-case
      snake->kebab))
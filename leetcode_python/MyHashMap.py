# Q706
class MyHashMap:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.keys = []
        self.values = []


    def put(self, key: int, value: int) -> None:
        """
        value will always be non-negative.
        """
        if key in self.keys:
            i = self.keys.index(key)
            self.values[i] = value
        else:
            self.keys.append(key)
            self.values.append(value)



    def get(self, key: int) -> int:
        """
        Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
        """
        if key in self.keys:
            return self.values[self.keys.index(key)]
        else:
            return -1


    def remove(self, key: int) -> None:
        """
        Removes the mapping of the specified value key if this map contains a mapping for the key
        """
        if key in self.keys:
            i = self.keys.index(key)
            del self.keys[i]
            del self.values[i]



# Your MyHashMap object will be instantiated and called as such:
# obj = MyHashMap()
# obj.put(key,value)
# param_2 = obj.get(key)
# obj.remove(key)

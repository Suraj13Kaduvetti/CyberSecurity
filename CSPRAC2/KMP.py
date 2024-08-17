def compute_lps(pattern):
    LPS = [0] * len(pattern)
    i = 0 
    j = 1  
    LPS[0] = 0   
    while j < len(pattern):     
        if pattern[i] == pattern[j]:        
            LPS[j] = i + 1
            i += 1
            j += 1
        else:         
            if i == 0:        
                LPS[j] = 0
                j += 1
            else:         
                i = LPS[i - 1]
    return LPS
pattern = "abcdabda"
LPS = compute_lps(pattern)
print("LPS Array:", LPS)

for i in range(len(pattern)):
    print(f"LPS[{i}] = {LPS[i]}")

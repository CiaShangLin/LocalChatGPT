from gpt4all import GPT4All
# model = GPT4All("orca-mini-3b-gguf2-q4_0.gguf")
model = GPT4All("gpt4all-13b-snoozy-q4_0.gguf")
output = model.generate("The capital of Taiwan is ", max_tokens=3)
print(output)
# hashcrack_mt

A small multi-threaded hash cracker written in Java. It recovers a plaintext from
its hash digest by brute-forcing lowercase alphabetic candidates and comparing the
computed digest against a target hash.

> **Disclaimer:** This is an educational project meant for learning about hashing,
> brute-force search, and Java concurrency. Only use it against hashes you are
> authorized to test.

## How it works

Candidates are generated over the alphabet `a–z`. Each candidate index is mapped to
a string of a fixed length, hashed with the requested algorithm (e.g. MD5), and the
resulting digest is compared to the target. When a match is found, the corresponding
plaintext is returned.

### Components

| File | Responsibility |
| --- | --- |
| `Main.java` | Entry point; runs a sample crack. |
| `Hashcrack.java` | Core cracking logic (single- and multi-threaded). |
| `CandidateGenerator.java` | Maps a numeric index to a candidate string and counts the search space. |

### Cracking modes

- **`MonoStyle(hash, length)`** — single-threaded search that iterates every
  candidate up to `length` characters using MD5.
- **`MultiThreadStyle(hash, length, algo)`** — splits the search space into zones
  and runs each on its own platform thread, stopping early once any thread finds a
  match (coordinated via an `AtomicBoolean`).

## Requirements

- JDK 17+ (uses `HexFormat`, `var`, and `Thread.ofPlatform()`).

## Build & run

Compile the sources and run the `Main` class:

```bash
# Compile
javac -d bin src/hashcrack_mt/*.java

# Run
java -cp bin hashcrack_mt.Main
```

The bundled example in `Main.java` cracks the MD5 hash
`de9b9ed78d7e2e1dceeffee780e2f919` with a maximum length of 10.

## Usage example

```java
String plaintext = Hashcrack.MultiThreadStyle(
    "de9b9ed78d7e2e1dceeffee780e2f919", // target hash
    10,                                  // candidate length
    "MD5"                                // digest algorithm
);
System.out.println(plaintext);
```

## Notes & limitations

- The candidate alphabet is restricted to lowercase letters `a–z`.
- `MultiThreadStyle` currently derives the thread count and the candidate length
  from the same `length` argument, so each thread searches words of exactly that
  length.
- Supported algorithms are those provided by the JVM's `MessageDigest`
  (e.g. `MD5`, `SHA-1`, `SHA-256`).

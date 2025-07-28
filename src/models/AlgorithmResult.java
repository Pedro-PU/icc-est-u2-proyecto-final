package models;

public class AlgorithmResult {
  private final String algorithmName;
  
  private final int pathLength;
  
  private final long timeMs;
  
  public AlgorithmResult(String paramString, int paramInt, long paramLong) {
    this.algorithmName = paramString;
    this.pathLength = paramInt;
    this.timeMs = paramLong;
  }
  
  public String getAlgorithmName() {
    return this.algorithmName;
  }
  
  public int getPathLength() {
    return this.pathLength;
  }
  
  public long getTimeMs() {
    return this.timeMs;
  }

  @Override
  public String toString() {
    return this.algorithmName + "," + this.pathLength + "," + this.timeMs;
  }

}

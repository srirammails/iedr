package pl.nask.crs.task;

/**
 *
 * @author Artur Kielak
 */
public class Task {
   private int id;
   private String name;
   private String schedule;
   
   public Task() {
      
   }

   public Task(int id, String name, String schedule) {
      this.id = id;
      this.name = name;
      this.schedule = schedule;
   }
   
   public static Task getInstance(String name, String schedule) {
     return new Task(-1, name, schedule);
   } 

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSchedule() {
      return schedule;
   }

   public void setSchedule(String schedule) {
      this.schedule = schedule;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }
   
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;
        Task task = (Task)o;
        if (id != task.getId())
            return false;
        if (!name.toLowerCase().equals(task.getName().toLowerCase()))
            return false;
        if (schedule.compareTo(task.getSchedule()) != 0)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Task[id: %s, name: %s, schedule: %s]", id, name, schedule);
    }
   
}

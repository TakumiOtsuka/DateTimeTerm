import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


class TermComparator implements Comparator<Term> {

  @Override
  public int compare(Term t1, Term t2) {
    return t1.getBegin().compareTo(t2.getBegin());
  }
}

public class Term {

  private LocalDateTime begin;
  private LocalDateTime end;

  public Term(LocalDateTime begin, LocalDateTime end) {
    this.begin = begin;
    this.end = end;
  }

  public LocalDateTime getBegin() {
    return begin;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  private static Term joinDateTime(List<Term> terms) {
    Collections.sort(terms, new TermComparator());
    //System.out.println("sorted");
    //for (Term term : terms) {
    //  System.out.println("begin: " + term.getBegin());
    //  System.out.println("end  : " + term.getEnd());
    //}
    return new Term(terms.get(0).getBegin(),
                    terms.get(terms.size() - 1).getEnd());
  }

  private static List<Term> getExcludeTerm(List<Term> terms) {
    List<Term> excludes = new ArrayList<Term>();
    Collections.sort(terms, new TermComparator());
    for (int i = 0; i < terms.size() - 1; i++) {
      excludes.add(new Term(terms.get(i).getEnd(), terms.get(i+1).getBegin()));
    }
    return excludes;
  }

  public static void main(String[] args) {
    Term t1 = new Term(LocalDateTime.of(2018, 10, 10, 00, 00),
                       LocalDateTime.of(2018, 10, 11, 00, 00));

    Term t2 = new Term(LocalDateTime.of(2018, 10, 12, 00, 00),
                       LocalDateTime.of(2018, 10, 12, 12, 00));

    System.out.println("t1.begin: " + t1.getBegin());
    System.out.println("t1.end  : " + t1.getEnd());
    System.out.println("t2.begin: " + t2.getBegin());
    System.out.println("t2.end  : " + t2.getEnd());

    List<Term> terms = new ArrayList<Term>();
    terms.add(t2);
    terms.add(t1);
    //System.out.println("not sorted");
    //for (Term term : terms) {
    //  System.out.println("begin: " + term.getBegin());
    //  System.out.println("end  : " + term.getEnd());
    //}

    Term joined = joinDateTime(terms);
    System.out.println("begin: " + joined.getBegin());
    System.out.println("end  : " + joined.getEnd());
    List<Term> excludes = getExcludeTerm(terms);
    System.out.println("excludes");
    for (Term exclude : excludes) {
      System.out.println("begin: " + exclude.getBegin());
      System.out.println("end  : " + exclude.getEnd());
    }
  }
}

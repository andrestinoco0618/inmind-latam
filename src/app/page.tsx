
import styles from "./page.module.css";
import Title from '@/components/Title/title';
import Form from "@/components/PsychologicalForm/psychologicalForm";
import Footer from "@/components/Footer/footer";

export default function Home() {
  return (
    <div>
      <section className={styles.section__title}>
        <Title></Title>
      </section>
      <section className={styles.section__form}>
        <Form></Form>
      </section>
      <section className={styles.section__footer}>
      <Footer></Footer>
      </section>
    </div>
  );
}

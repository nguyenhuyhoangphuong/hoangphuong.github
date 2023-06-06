using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using csdl.Models;

namespace csdl.Controllers.Client
{
    public class danhmuc_spController : Controller
    {
        private csdl1Entities db = new csdl1Entities();

        // GET: danhmuc_sp
        public ActionResult Index()
        {
            return View(db.danhmuc_sp.ToList());
        }

        // GET: danhmuc_sp/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            danhmuc_sp danhmuc_sp = db.danhmuc_sp.Find(id);
            if (danhmuc_sp == null)
            {
                return HttpNotFound();
            }
            return View(danhmuc_sp);
        }

        // GET: danhmuc_sp/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: danhmuc_sp/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "iddm,tendm,hinhanh,ghichu")] danhmuc_sp danhmuc_sp)
        {
            if (ModelState.IsValid)
            {
                db.danhmuc_sp.Add(danhmuc_sp);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(danhmuc_sp);
        }

        // GET: danhmuc_sp/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            danhmuc_sp danhmuc_sp = db.danhmuc_sp.Find(id);
            if (danhmuc_sp == null)
            {
                return HttpNotFound();
            }
            return View(danhmuc_sp);
        }

        // POST: danhmuc_sp/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "iddm,tendm,hinhanh,ghichu")] danhmuc_sp danhmuc_sp)
        {
            if (ModelState.IsValid)
            {
                db.Entry(danhmuc_sp).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(danhmuc_sp);
        }

        // GET: danhmuc_sp/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            danhmuc_sp danhmuc_sp = db.danhmuc_sp.Find(id);
            if (danhmuc_sp == null)
            {
                return HttpNotFound();
            }
            return View(danhmuc_sp);
        }

        // POST: danhmuc_sp/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            danhmuc_sp danhmuc_sp = db.danhmuc_sp.Find(id);
            db.danhmuc_sp.Remove(danhmuc_sp);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
